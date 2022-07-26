package com.recipes.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "RECIPES")
@Data
public class Recipes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private int recipeId;

	@Column(name = "recipe_name")
	private String recipeName;

	@Column(name = "rating")
	private int rating;

	@Column(name = "dish_type")
	private String dishType;

	@Column(name = "instructions")
	private String instructions;

	@JsonIgnore
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<FavoriteRecipes> favoriteRecipes;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "RECIPE_INGREDIENTS", joinColumns = { @JoinColumn(name = "recipe_id_fk") }, inverseJoinColumns = {
			@JoinColumn(name = "ingredients_id_fk") })
	private List<Ingredients> ingredients;

}
