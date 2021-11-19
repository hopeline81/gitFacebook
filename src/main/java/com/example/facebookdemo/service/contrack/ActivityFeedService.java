package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.ActivityFeedDTO;
import com.example.facebookdemo.entity.User;

public interface ActivityFeedService {

    ActivityFeedDTO showActivityFeedDTO(User user);
}
