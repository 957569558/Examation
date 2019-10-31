package com.zzkk.service;

import com.zzkk.mapper.UserMapper;
import com.zzkk.model.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author warmli
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUser(String number){
        return userMapper.getUser(number);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean importUser(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<User> userList = new ArrayList<User>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new IOException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        User user;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            user = new User();
            String uclass=new String();
            String number=new String();
            String password=new String();
            String uname = new String();

            if(row.getCell(0)!=null){
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                uclass = row.getCell(0).getStringCellValue();
            }

            if(row.getCell(1)!=null){
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                number = row.getCell(1).getStringCellValue();
            }

            if(row.getCell(2)!=null){
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                uname = row.getCell(2).getStringCellValue();
            }

            if(row.getCell(3)!=null){
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                password = row.getCell(3).getStringCellValue();
            }

            user.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
            user.setUclass(uclass);
            user.setNumber(number);
            user.setPassword(password);
            user.setUname(uname);
            userList.add(user);
        }
        for (User userResord : userList) {
            String number = userResord.getNumber();
            int cnt = userMapper.countByNumber(number);
            if (cnt == 0) {
                userMapper.addUser(userResord);
                System.out.println(" 插入 "+userResord);
            } else {
                userMapper.updateUserByNumber(userResord.getNumber());
                System.out.println(" 更新 "+userResord);
            }
        }
        return notNull;
    }
}
