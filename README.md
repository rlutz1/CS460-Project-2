# CS460-Project-2

CS460 Project 2: Gym Management. This repository is for housing all documentation and inevitable codebase when building a demo.

## Code Files

The code base for the demo is split into the following primary packages.

+ **Driver** - All initialization and driving code (main method in `Driver.java`).
+ **GSMS** - All backend system code (all components from our GSMS SAD Architecture diagram).
+ **Instructor Application** - All backend code for an Instructor Application (according to its SAD architecture).
+ **Member Application** - All backend code for a Member Application (according to its SAD architecture).
+ **Gym** - All front end code and mimicked onsite components needed to simulate a real physical gym.

The code within these packages strictly adhere to the architecture outlined within our documents.

## Demo 

NOTE: the slides of our presentation can be found under `documentation/demo-slides` in this repository if desired in both PDF and PPTX format.

The demo was built in such a way as to tie in all of our use cases into a cohesive script to show our baseline functionality. We start by running through a member who has typically had low energy in class, generating a workout at home. The low engagement has been registered in class from member wearable signals and logged; therefore, the workout recommendation is a low-energy, gentle workout (Use Case 3). But the member has second thoughts of shame and decides to go to class anyway. This leads to scenarios of our target member experiencing exhaustion (Use Case 1), a conflict arising (Use Case 2), and an inevitable collapse/health emergency in our member (Use Case 4). Thankfully, the instructor uses their better judgment to end class after this final event. However, an instructor at the front desk has been noticing issues from that class for some time, and decides to generate a report about that class and instructor over the last month (Use Case 5).
### Demo Decisions & Justifications
#### Demo Inclusions
##### Actual Components
We implemented the EventAnalyzer alongside its subcomponents, such as Classroom, Audio Listener, Video Listener, and Wearable Listener, in order to capture signals from wearables, camera, and audio devices to be able to feed them into the LiveEventAI wrapper component that communicates with the simulated AI. We also implemented the NotificationDispatcher to be able to send notifications to appropriate members and instructors affected by an event officially recognized by the EventAnalyzer as notification-worthy.

Further, we implemented in a limited capacity the Recommendation Dispatcher and Recommendation AI wrapper for the initial starting point of the demonstration. The Data Manager and Report AI wrapper additionally had limited functionality to be able to showcase the generation of a report. Both of these channels of communication are implemented as detailed in our architecture, receiving information via application, to API, to GSMC, and finally to the component to interact with their respective AI, pushing out the response to the appropriate application. Consequently, our Gym Space Management Controller component was implemented to capture these application requests.
##### Substituted Components
We substitute on-site hardware, such as audio sensors, wearables, and cameras, with backend objects with corresponding frontend visuals as appropriate. These did, however, send signals to the backend listener components as specified by our SAD architecture. We also utilized graphical objects and JavaFX Transitions [1] to add dynamic members and instructors and enhance user feedback to our gym space demonstration.

Further, instead of real mobile applications communicating over the network to our system, Instructor and Member Applications were simply simulated as additional windows on the main GUI execution thread. Consequently, due to the lack of actual network connections to real user applications, we resulted in the Member/Instructor Dispatchers not having any sort of actual socket-connection code and, instead, these would simulate recognizing the correct windows that simulated either an  Instructor or Member Application.

We simulated our actual (blackbox) AI for the EventAnalyzer to consult with, via joining its functionality within the LiveEventAI component - hence, the LiveEventAI is a hybrid of a wrapper that also outputs a simulated AI response for the purpose of demo use cases. This also stands for RecommendationAI and ReportAI.
#### Demo Exclusions
+ Doorway sensors that pick up the RFID signals from member wearables are not included in this demo. The resulting backend logic surrounding attendance and building monitoring has therefore also not been included.
+ Creating and analyzing an instructor-created itinerary is not included.
+ The Logger component - most use cases shown were showcased with the assumption of there being data already logged in the datastore.
+ The Database has not been included for simplicity.
+ No real LLM was programmatically accessed in the demonstration.
### Illustrated Use Cases
1. Generate a Recommendation for Auxiliary Workout
Showcase a member interaction with the system and one of our main features of AI-driven recommendation generation and workout analysis, utilizing individuals’ data to personalize and ensure safety.
2. Resolving Exhaustion in Cardio Class
Showcase our main functionality of AI-driven member wearable signal analysis and notification system.
3. Conflict Detection and De-escalation
Showcase our main functionality of AI-driven camera and audio signal analysis and notification system.
4. Detecting and Responding to a Member Health Emergency
Showcase swift analysis and reaction time in our notification and event analysis system, providing a second set of eyes for instructors in the room when a timely response to emergencies is required.
5. Generating a Monthly Usage PDF Report
Showcase that the system can support the usage and trend information for attendants in the gym based on the wealth of static stored information and the dynamic collection of on-site events.
### Technologies Used
+ Java - Sole programming language used.
+ JavaFX - a framework to assemble and interact with our GUI.
+ IntelliJ - IDE for Java and running/showcasing our GUI demo.
+ Git/GitHub - code versioning and code history with a repository.

### AI Usage
The AI usage for this demo was strictly in the use cases for (1) generating a workout and (2) generating a report. The code is not hooked to a live API, but the static output was generated by ChatGPT, model GPT-5.5 Instant.

Generating a workout was generated by the following prompt:

_Could you generate a workout recommendation for me? I've been feeling very low energy lately, pretty unenthused in my regular workout classes. But I still feel like I should do something at home today._

This prompt was used to simulate the idea that we would have logs of low engagement in classes from the members wearable and thus would be taken into account into recommendation generation unless specified otherwise by the member.

Generating a report was gathered by the following prompt:

_I have an instructor of a gym aerobics class named Jane Fonda. She has had a strangely high amount of safety, health, and conflict emergencies in the last month. Can you write a brief synopsis of faked events over the last month that have occurred in her classroom?_

Then, when it did not want to generate using a real person’s name:

_The same request, but her name is Jane Doe._

And then the name was replaced for the demo.
This prompt was used to be consistent with the demo storyline since all the issues are occurring in this instructor’s class, and to mimic a likely problematic class in a real gym situation with a negligent instructor.

### References

[1] “Transition (JavaFX 8),” Oracle.com, 2026. https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Transition.html (accessed May 06, 2026).

