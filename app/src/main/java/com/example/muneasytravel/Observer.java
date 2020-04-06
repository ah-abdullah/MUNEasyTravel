package com.example.muneasytravel;

/**
 * Observer to update the application as required
 * In our case, ShowCourseActivity and ImportantLocationActivity are updated with the help of UpdateCourseObserver and UpdateImportantLocationObserver extending this class
 */
public abstract class Observer {
    public abstract void update();
}
