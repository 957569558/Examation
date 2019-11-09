package com.zzkk.service;

import com.zzkk.mapper.UserMapper;
import com.zzkk.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author warmli
 */
@Service
public class RegisterService {
    @Autowired
    UserMapper userMapper;

    public List<User> getRegisterUser(String ename){
        return userMapper.getRegisterUser(ename);
    }

    public String downloadFile(HttpServletResponse response, String ename){
        File file = saveFile(getRegisterUser(ename));
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }

            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "registerOp";
    }

    public File saveFile(List<User> users){
        System.out.println(users);
        File file = null;
        file = new File("D:/报名信息.xlsx");/*ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "报名信息.xlsx");*/

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream stream = new FileOutputStream(file)) {
            Workbook workbook = new XSSFWorkbook();
            String[] title = {"班级","学号","姓名","身份证号"};
            String sheetName = "报名信息";
            Sheet sheet = workbook.createSheet(sheetName);
            Row titleRow = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Cell cell = null;
            for(int i = 0; i < title.length; i++){
                cell = titleRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            Row row = null;
            for (int i = 0; i < users.size(); i++) {
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(users.get(i).getUclass());
                row.createCell(1).setCellValue(users.get(i).getNumber());
                row.createCell(2).setCellValue(users.get(i).getUname());
                row.createCell(3).setCellValue(users.get(i).getPassword());
            }

            for (int i = 0; i < title.length; i++){
                sheet.autoSizeColumn(i, true);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
            }

            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
