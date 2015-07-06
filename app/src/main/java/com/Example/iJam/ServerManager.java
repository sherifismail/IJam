package com.Example.iJam;

/**
 * Created by Mostafa on 7/5/2015.
 */
public class ServerManager {
    private static String serverStatus = null;
    private static String error = null;

    public static void setServerStatus(String status){
        serverStatus = status;
    }

    public static void setError(String error) {
        ServerManager.error = error;
    }

    public static String getServerURL() {
        //return "http://10.40.35.117/Jamhub";
        //return "http://192.168.1.6/Jamhub";
        return "http://192.168.1.73/Jamhub";
    }

    public static String getServerStatus() {
        return serverStatus;
    }

    public static String getError() {
        return error;
    }
}
