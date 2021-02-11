package com.baidu.service;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> getAllUser(int currentPage, int pageSize);
}
