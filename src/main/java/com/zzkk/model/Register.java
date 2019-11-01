package com.zzkk.model;

import lombok.Data;

/**
 * @author warmli
 */
@Data
public class Register {
    public Register() {
    }

    public Register(String rid, String uid, String eid) {
        this.rid = rid;
        this.uid = uid;
        this.eid = eid;
    }

    private String rid;

    private String uid;

    private String eid;
}
