/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.security;

/**
 *
 * @author faruq
 */
public enum AppUserPermissions {

    /**
     *
     */
    CANDIDATE_READ("candidate:read"),
    CANDIDATE_WRITE("candidate:write"),;
    
    
    private String Permission;

    private AppUserPermissions(String Permission) {
        this.Permission = Permission;
    }

    /**
     * @return the Permission
     */
    public String getPermission() {
        return Permission;
    }

    /**
     * @param Permission the Permission to set
     */
    public void setPermission(String Permission) {
        this.Permission = Permission;
    }
    

    }
