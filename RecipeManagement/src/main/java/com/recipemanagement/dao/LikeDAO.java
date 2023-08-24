package com.recipemanagement.dao;

import com.recipemanagement.exception.LikeNotFoundException;
import com.recipemanagement.model.Like;

import java.util.List;

public interface LikeDAO {
    void addLike(Like like);

    void deleteLike(Long likeId) throws LikeNotFoundException;

    Like getLikeById(Long likeId) throws LikeNotFoundException;

    List<Like> getAllLikes();
}