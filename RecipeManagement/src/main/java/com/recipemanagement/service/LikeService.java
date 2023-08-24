package com.recipemanagement.service;

import com.recipemanagement.exception.LikeNotFoundException;
import com.recipemanagement.model.Like;

import java.util.List;

public interface LikeService {
    void addLike(Like like);

    void deleteLike(Long likeId) throws LikeNotFoundException;

    Like getLikeById(Long likeId) throws LikeNotFoundException;

    List<Like> getAllLikes();

    int getLikeCountForRecipe(Long recipeId);
}