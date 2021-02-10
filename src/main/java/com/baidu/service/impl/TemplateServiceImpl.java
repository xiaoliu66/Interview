package com.baidu.service.impl;

import com.baidu.service.TemplateService;
import com.baidu.utils.POIUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    /**
     * 只读取第一列的数据
     * @param excelFile
     * @return
     */
    @Override
    public List<String> getContext(MultipartFile excelFile) {
        List<String> result = new ArrayList<>();
        try {
            List<String[]> list = POIUtils.readExcel(excelFile, 0, 0);
            for (String[] strings : list) {
                for (String string : strings) {
                    result.add(string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
