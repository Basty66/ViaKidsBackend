package com.viakids.model.dto;

import com.viakids.model.AttendanceRecord;

public class AttendanceScanResponse {
    private boolean success;
    private AttendanceRecord data;
    private StudentStatusResponse studentStatus;
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public AttendanceRecord getData() { return data; }
    public void setData(AttendanceRecord data) { this.data = data; }
    
    public StudentStatusResponse getStudentStatus() { return studentStatus; }
    public void setStudentStatus(StudentStatusResponse studentStatus) { this.studentStatus = studentStatus; }
    
    public static class StudentStatusResponse {
        private String status;
        private String lastAction;
        private String lastTime;
        private AttendanceRecord record;
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getLastAction() { return lastAction; }
        public void setLastAction(String lastAction) { this.lastAction = lastAction; }
        
        public String getLastTime() { return lastTime; }
        public void setLastTime(String lastTime) { this.lastTime = lastTime; }
        
        public AttendanceRecord getRecord() { return record; }
        public void setRecord(AttendanceRecord record) { this.record = record; }
    }
}
