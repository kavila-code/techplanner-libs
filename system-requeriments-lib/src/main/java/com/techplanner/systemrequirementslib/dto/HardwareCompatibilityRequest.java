package com.techplanner.systemrequirements.dto;

public class HardwareCompatibilityRequest {

    private int ramGb;
    private int storageGb;
    private boolean tpm;
    private boolean secureBoot;
    private String architecture;
    private String cpu;
    private String gpu;

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public int getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(int storageGb) {
        this.storageGb = storageGb;
    }

    public boolean isTpm() {
        return tpm;
    }

    public void setTpm(boolean tpm) {
        this.tpm = tpm;
    }

    public boolean isSecureBoot() {
        return secureBoot;
    }

    public void setSecureBoot(boolean secureBoot) {
        this.secureBoot = secureBoot;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}