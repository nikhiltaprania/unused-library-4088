package com.recipemanagement.service;

import com.recipemanagement.dao.LikeDAO;
import com.recipemanagement.exception.LikeNotFoundException;
import com.recipemanagement.model.Like;

import java.util.List;

public class LikeServiceImpl implements LikeService {
    private final LikeDAO likeDAO;

    public LikeServiceImpl(LikeDAO likeDAO) {
        this.likeDAO = likeDAO;
    }

    @Override
    public void addLike(Like like) {
        likeDAO.addLike(like);
    }

    @Override
    public void deleteLike(Long likeId) throws LikeNotFoundException {
        likeDAO.deleteLike(likeId);
    }

    @Override
    public Like getLikeById(Long likeId) throws LikeNotFoundException {
        return likeDAO.getLikeById(likeId);
    }

    @Override
    public List<Like> getAllLikes() {
        return likeDAO.getAllLikes();
    }

    @Override
    public int getLikeCountForRecipe(Long recipeId) {
        return likeDAO.getAllLikes().stream()
                .filter(like -> like.getRecipe().getId().equals(recipeId))
                .mapToInt(like -> 1)
                .sum();
    }
}