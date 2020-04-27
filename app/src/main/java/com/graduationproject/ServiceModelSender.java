package com.graduationproject;

import android.content.Context;

import com.graduationproject.Model.SendVideoServiceModel;

public class ServiceModelSender
{
    private static ServiceModelSender instance;

    private SendVideoServiceModel model;

    public static ServiceModelSender getinstace()
    {
        if(instance == null)
        {
            synchronized (ServiceModelSender.class) {
                if(instance == null)
                    instance =new ServiceModelSender();
            }
        }
        return instance;
    }


    public void setmodel(SendVideoServiceModel model)
    {
        this.model = model;
    }


    public SendVideoServiceModel getModel()
    {
        return model;
    }

}
