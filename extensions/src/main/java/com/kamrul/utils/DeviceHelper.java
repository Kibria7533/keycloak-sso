package com.kamrul.utils;

import com.kamrul.dtos.Customer;
import com.kamrul.dtos.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceHelper {

    public static List<Customer> VALID_EMAILS = new ArrayList<>();

    static {
        VALID_EMAILS.add(new Customer("JON", "DOE", "kamrul@gmail.com", getUserADevices()));
        VALID_EMAILS.add(new Customer("BLACK", "PANTHER", "joe@gmail.com", getUserBDevices()));
        VALID_EMAILS.add(new Customer("WYCLIFFE", "BUSH", "eric@gmail.com", getUserADevices()));
        VALID_EMAILS.add(new Customer("PUTIN", "VLADIMIR", "putin@gmail.com", getUserBDevices()));
    }

    public static List<Device> getUserADevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device("Device A", " Model A"));
        devices.add(new Device("Device B", " Model B"));
        devices.add(new Device("Device C", " Model C"));
        devices.add(new Device("Device D", " Model D"));
        devices.add(new Device("Device E", " Model E"));

        // 5 devices ....
        return devices;
    }


    public static List<Device> getUserBDevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device("Device A", " Model A"));
        devices.add(new Device("Device B", " Model B"));

        // 2 devices ....
        return devices;
    }
}
