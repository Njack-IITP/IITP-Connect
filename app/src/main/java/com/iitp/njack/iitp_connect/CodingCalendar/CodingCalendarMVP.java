package com.iitp.njack.iitp_connect.CodingCalendar;

/**
 * Created by srv_twry on 19/8/17.
 * This class contains the interfaces for implementing the MVP pattern in the Coding-Calendar.
 */

public class CodingCalendarMVP {

    /*
    * Implemented by the view and used by the presenter to change the view.
    * */
    public interface RequiredViewOperations{

    }

    /*
    * Implemented by the Presenter and used by the View to do actions.
    * */
    public interface ProvidedPresenterOperations{

    }

    /*
    * Implemented by the Model and used by the Presenter to request the data from the model.
    * */
    public interface ProvidedModelOperations{

    }

    /*
    * Implemented by the Presenter and used by the Model to send the requested data back.
    * */
    public interface RequiredPresenterOperations{

    }
}
