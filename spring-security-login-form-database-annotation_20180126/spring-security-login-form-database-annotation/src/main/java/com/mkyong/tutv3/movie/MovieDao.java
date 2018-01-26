package com.mkyong.tutv3.movie;

public interface MovieDao{
	
	Movie findByDirector(String name);

	Movie findByDirector2(String name);
	
}