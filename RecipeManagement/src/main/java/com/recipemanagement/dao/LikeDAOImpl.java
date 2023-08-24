package com.recipemanagement.dao;

import com.recipemanagement.exception.LikeNotFoundException;
import com.recipemanagement.model.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class LikeDAOImpl implements LikeDAO {
    private final EntityManager em;

    public LikeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addLike(Like like) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            em.persist(like);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteLike(Long likeId) throws LikeNotFoundException {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            Like like = em.find(Like.class, likeId);
            if (like == null) {
                throw new LikeNotFoundException("Like with ID " + likeId + " not found.");
            }
            em.remove(like);

            et.commit();

        } catch (Exception e) {
            throw new LikeNotFoundException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Like getLikeById(Long likeId) throws LikeNotFoundException {
        try {
            Like like = em.find(Like.class, likeId);
            if (like == null) {
                throw new LikeNotFoundException("Like with ID " + likeId + " not found.");
            }
            return like;

        } catch (Exception e) {
            throw new LikeNotFoundException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Like> getAllLikes() {
        List<Like> likes = null;
        try {
            Query query = em.createQuery("SELECT l FROM Like l");
            likes = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return likes;
    }
}