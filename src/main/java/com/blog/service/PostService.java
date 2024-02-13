package com.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.blog.repository.PostJpaRepository;
import com.blog.repository.PostRepository;
import com.blog.vo.Post;

@Service
public class PostService {
	private static List<Post> posts;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostJpaRepository postJpaRepository;
	
	public Post getPost(Long id) {
		Post post = postJpaRepository.findOneById(id);
		
		return post;
	}
	
	public List<Post> getPosts() {
		List<Post> posts = postJpaRepository.findAllByOrderByUpdtDateDesc();
		
		return posts;
	}
	
	public List<Post> getPostsOrderByUpdtAsc() {
		List<Post> postList = postJpaRepository.findAllByOrderByUpdtDateAsc();
		
		return postList;
	}
	
	public List<Post> getPostsOrderByRegDesc() {
		List<Post> postList = postRepository.findPostOrderByRegDateDesc();
		
		return postList;
	}
	
	public List<Post> searchPostByTitle(String query) {
		List<Post> posts = postJpaRepository.findByTitleContainingOrderByUpdtDateDesc(query);
		
		return posts;
	}
	
	public List<Post> searchPostByContent(String query) {
		List<Post> postList = postJpaRepository.findByContentContainingOrderByUpdtDateDesc(query);
		
		return postList;
	}
	
	public boolean savePost(Post post) {
		Post result = postJpaRepository.save(post);
		boolean isSuccess = true;
		
		if(result == null) {
			isSuccess = false;
		}
		
		return isSuccess;
	}
	
	public boolean deletePost(Long id) {
		Post result = postJpaRepository.findOneById(id);

		if(result == null) {
			return false;
		}
		
		postJpaRepository.deleteById(id);
		return true;
	}
	
	public boolean updatePost(Post post) {
		Post result = postJpaRepository.findOneById(post.getId());
		
		if(result == null)
			return false;
		
		if(!StringUtils.isEmpty(post.getTitle())) {
			result.setTitle(post.getTitle());
		}
		
		if(!StringUtils.isEmpty(post.getContent())) {
			result.setContent(post.getContent());
		}
		
		postJpaRepository.save(result);
		
		return true;
	}
}
