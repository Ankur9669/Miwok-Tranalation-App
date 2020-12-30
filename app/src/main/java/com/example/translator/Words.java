package com.example.translator;

public class Words
{
    private static final int NO_IMAGE = -1;
    private String miwok = "";
    private String english = "";
    private int resourceId = NO_IMAGE;
    private int audioResourceId = -1;

    public Words()
    {

    }

    public Words(String miwok, String english, int audioResourceId)
    {
        this.english = english;
        this.miwok = miwok;
        this.audioResourceId = audioResourceId;
    }

//    public Words(String miwok, String english, int resourceId)
//    {
//        this.english = english;
//        this.miwok = miwok;
//        this.resourceId = resourceId;
//    }

    public Words(String miwok, String english, int resourceId, int audioResourceId)
    {
        this.english = english;
        this.miwok = miwok;
        this.resourceId = resourceId;
        this.audioResourceId = audioResourceId;
    }

    public String getMiwokTranslation()
    {
        return this.miwok;
    }

    public String getDefaultTranslation()
    {
        return this.english;
    }

    public int getResourceId()
    {
        return resourceId;
    }

    public boolean hasImage()
    {
        if(resourceId == NO_IMAGE)
        {
            return false;
        }
        return true;//resourceId != NO_IMAGE;
    }

    public int getAudioResourceId()
    {
        return audioResourceId;
    }
}
