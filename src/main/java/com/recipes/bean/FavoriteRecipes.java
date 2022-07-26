package com.recipes.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "FAVORITE_RECIPES")
@Data
public class FavoriteRecipes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fav_id")
	private int favId;

	@Column(name = "rating")
	private int rating;

	@Column(name = "servings")
	private int servings;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "recipe_id")
	private Recipes recipe;

}
