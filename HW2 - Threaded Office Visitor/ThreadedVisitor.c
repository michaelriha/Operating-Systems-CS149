/* 
 * File:   ThreadedVisitor.c
 * Author: Michael Riha 
 * Use multithreading via pthreads library to simulate how students could randomly
 * visit a professor during their office hours such that the students are seen 
 * in the order they arrive, but only one can be in the office at a time.
 * 
 * Code is sloppy and hacked together because I've wasted too much time on this 
 * assignment to make it look pretty. It works 100% including the extra credit
 * except for one very rare race condition (or not?) at time 60.
 */
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>

#define num_students 20 // generate 15 students
#define max_time 60     // 60 minutes of office time
#define max_wait 3      // 3 students can wait
#define max_threads (max_wait + 1)
#define max_output_len 20 // maximum length of output for one thread's status
#define buf_len 5       // minutes to buffer output before printing
#define line_len 80    
#define leave_time 10  // time til a student leaves

char visit_office(int i, int tnum);
void* visitor(void* param);
void queue_add(int i);
void queue_poll();

typedef struct _student student;
struct _student
{
    unsigned int arrival_time;
    unsigned int duration;
    char is_visiting;
};

typedef struct _thread thread;
struct _thread
{
    pthread_t tid;
    pthread_attr_t tattr;
    int param;
};

student *students;
thread *threads;

int minutes = 1;             // how many minutes into the office hour
int threads_running = 0;     
int last_thread_closed = 0;  // used to assign new thread to the correct spot in output
int minutes_past_closing = 0; // how long since office closed - used for output
int queue[max_threads];      // thread ids (student #) that are running

char office_available = 1;   // if the office can be enterred by a st udent
char office_closed = 0;      // if the office is closed for good
char* output[max_time+10][max_threads];// output[Time][Thread#] -> Thread status
char* main_output[max_time+10]; // for Main: output statements

pthread_mutex_t mutex;
pthread_mutex_t mutex2;
/*
 * Use multithreading via pthreads library to simulate how students could randomly
 * visit a professor during their office hours such that the students are seen 
 * in the order they arrive, but only one can be in the office at a time.
 */
int main(int argc, char** argv) 
{
    srand(0);
    students = calloc(num_students, sizeof(student));
    threads = calloc(num_students, sizeof(thread));
    
    for (int i = 0; i < max_threads; ++i)
        queue[i] = -1;
    for (int i = 0; i < max_time+10; ++i)
        main_output[i] = malloc(line_len);

    // generate some students with random visit and arrival times
    // also generate threads but don't run them yet
    int arrival = 0;
    printf("Student # | Arrival | Duration\n");
    printf("----------+---------+---------\n");
    for (int i = 0; i < num_students; ++i)
    {
        students[i].arrival_time = arrival;
        students[i].duration = rand() % 5 + 2;
        students[i].is_visiting = 0;
        arrival += rand() % 5 + 1;
        printf("    %-2d    |    %-2d   |    %-2d   \n", i, arrival, students[i].duration);
        threads[i].param = i;
        pthread_attr_init(&threads[i].tattr);
    }
    printf("\nBuffering output for %d seconds\n", buf_len);
    printf("Minute   Thread 0            Thread 1            Thread 2            Thread 3\n");
    printf("-----------------------------------------------------------------------------\n");
    int next = 0;    // next student in line
    while (!office_closed)
    {
        // run threads for students that have arrived
        while (next < num_students && students[next].arrival_time <= minutes)
        {
            if (threads_running < max_threads)
            {
                pthread_create(&threads[next].tid, &threads[next].tattr, visitor, &threads[next].param);
                queue_add(next);
            }
            else
            {
                sprintf(main_output[minutes], "  %-2d     Main: Student %d left.\0", minutes+1, next);
            }
            next++;
        }
        int m = minutes++;
        
        // allow strings to buffer some before printing
        // I would have put this in a print(int m) method but it was causing issues
        // and this already works
        if (m >= buf_len)
        {
            if (main_output[m - buf_len][0] != '\0')
                printf("%s\n", main_output[m - buf_len]);
            printf("  %-2d", m - buf_len + 1);
            char* out = malloc(line_len);
            char parfore = 1;
            for (int i = 0; i < max_threads; ++i)
            {
                char* part = malloc(max_output_len);
                if (output[m - buf_len][i] != NULL)
                {
                    parfore = 0;
                    sprintf(part, "     %-15s\0", output[m - buf_len][i]);
                }
                else
                    sprintf(part, "     %-15s\0", "   N/A");                
                strcat(out, part);
                free(part);
            }
            if (parfore)
            {
                sprintf(out, "     No Students Visiting: Professor Works on ParFore");
                if (m > max_time)
                    office_closed = 1;
            }
            printf("%s", out);
            free(out);
            printf("\n");
        }
        //usleep(10000)
        sleep(1);
    } 
    
    // print remaining text out
    for (int m = minutes - buf_len; m < max_time + minutes_past_closing; ++m)
    {
        if (main_output[m][0] != '\0')
            printf("%s\n", main_output[m]);
        printf("  %-2d", m + 1);
        char* out = malloc(line_len);
        char parfore = 1;
        for (int i = 0; i < max_threads; ++i)
        {
            char* part = malloc(max_output_len);
            if (output[m][i] != NULL)
            {
                if (threads_running == 0)
                    parfore = 0;
                sprintf(part, "     %-15s\0", output[m][i]);
            }
            else
                sprintf(part, "     %-15s\0", "   N/A");                
            strcat(out, part);
            free(part);
        }
        if (parfore)
            sprintf(out, "     No Students Visiting: Professor Works on ParFore");
        printf("%s", out);
        free(out);
        printf("\n");
    }
    printf("Main: Done!");
}

/** a thread for a visitor to the office hours
 * Void* param: expects an int representing the student # that runs on this thread
 * return: NULL
 */
void* visitor(void* param)
{
    int i = *(int*) param;   
    int m = minutes - 1;
    // this thread's number -- used for output. increment after so no duplicates
    int tnum = last_thread_closed++ % max_threads;
    sprintf(main_output[m], "  %-2d     Main: Start thread %d (Student #%d)\0", m+1, tnum, i);
    
    // continually try to visit the office until an exit event occurs
    while(1)
    {
        if (visit_office(i, tnum) == 0)
            break;
        usleep(950000); // sleep JUST less than 1s so threads don't "disappear"
    }
    
    // Add a close thread message to any existing Main: message
    m = minutes - 1;
    if (main_output[m][0] == '\0')
        sprintf(main_output[m], "  %-2d     Main: Close thread %d (Student #%d)\0", m+1, tnum, i);
    else
    {
        char* out2 = malloc(line_len);
        sprintf(out2, "\n  %-2d     Main: Close thread %d (Student #%d)\0", m+1, tnum, i);
        strcat(main_output[m], out2);
    }
    
    if (m >= max_time)
    {
        minutes_past_closing++;
        office_closed = 1;
    }
    // Close the thread
    last_thread_closed = tnum;
    queue_poll();
    pthread_join(threads[i].tid, NULL);
}

/** helper function for visitors to do stuff. Handles almost all of the output
 * and who can visit / who can't etc.
 * int i : index of the student running on this thread
 * int tnum: the thread "number" that is running (used to align output)
 * return: char - 0 if terminated, 1 if still running
 */
char visit_office(int i, int tnum)
{   
    int m = minutes-1;
    if (students[i].is_visiting)
    {
        if (students[i].duration > 0)
        {
            if (m >= max_time)
                minutes_past_closing++;
            output[m][tnum] = "Visiting\0";
            students[i].duration--;
        }
        else // visit is over
        {            
            students[i].is_visiting = 0;
            output[m][tnum] = "Ending visit\0";
            
            pthread_mutex_lock(&mutex);
            office_available = 1;
            pthread_mutex_unlock(&mutex);
            return 0; 
        }
    }
    else
    {   
        pthread_mutex_lock(&mutex2); // lock queue
        if(i == queue[0] && office_available)
        {            
            pthread_mutex_unlock(&mutex2);
            output[m][tnum] = "Starting visit\0";
            students[i].is_visiting = 1;
            students[i].duration--;
            
            pthread_mutex_lock(&mutex);
            if (m >= max_time)
                minutes_past_closing++;
            office_available = 0;
            pthread_mutex_unlock(&mutex);
        }
        else
        {
            pthread_mutex_unlock(&mutex2);
            if (m >= max_time - 1)
            {
                output[m][tnum] = "Left:OffClosed\0";
                return 0;
            }
            else if (m - students[i].arrival_time >= leave_time)
            {
                output[m][tnum] = "Left:Waited10s\0";
                return 0;
            }
            else
                output[m][tnum] = "Waiting\0";
        }    
    }
    return 1;
}

/** Add a thread/student to queue queue
 * int student : the student to add to queue
 */
void queue_add(int student)
{
    pthread_mutex_lock(&mutex2);
    int j;
    for (j = 0; j < max_threads-1 && queue[j] != -1; ++j);    
    threads_running++;
    queue[j] = student;
    pthread_mutex_unlock(&mutex2);
}

// Remove the oldest thread from queue queue and shift values to the left
void queue_poll()
{
    pthread_mutex_lock(&mutex2);
    threads_running--;
    int i;
    for (i = 0; i < max_threads - 1 && queue[i+1] != -1; ++i)
        queue[i] = queue[i+1];
    queue[i] = -1;
    pthread_mutex_unlock(&mutex2);
}