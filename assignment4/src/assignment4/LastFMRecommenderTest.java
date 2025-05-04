package assignment4;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class LastFMRecommenderTest {

	static LastFMRecommender friend = new LastFMRecommender("user_friends.dat","user_artists.dat", "artists.dat");


	@Test
	void listFriendTest() {
		
		Iterable<Integer> actualIterable = friend.listFriends(2);
		

		System.out.println("Friends of user 2: ");
		List<Integer> actual = new ArrayList<>();
		
		for(int x : actualIterable) {
			actual.add(x);
			System.out.println(x);
		}
		
		System.out.println();
		
		List<Integer> expected = Arrays.asList(1869,1625,1585,1327,1230,1210,1209,909,831,761,515,428,275);

		assertEquals(actual,expected);
		
	}
	
	
	
	@Test
	void listCommonFriendsTest() {
		Iterable<Integer> actualIterable = friend.commonFriends(2, 3);
		List<Integer> actual = new ArrayList<>();
		for(int x : actualIterable) {
			actual.add(x);
		}
		
		List<Integer> expected = Arrays.asList(428,275);
		
		assertEquals(actual,expected);
		
		

		
	}
	
	
	
	@Test
	void listCommonArtistsTest() {
		Iterable<Integer> actualIterable = friend.listArtists(2, 4);
		
		List<Integer> actual = new ArrayList<>();
		for(int x : actualIterable) {
			actual.add(x);
		}
		
		List<Integer> expected = Arrays.asList(77,72,70,65,64,53,51);
		
		assertEquals(actual,expected);
		
	}
	
	
	
	@Test
	void top10Test() {
		Iterable<Integer> actualIterable = friend.top10();
		
		List<Integer> actual = new ArrayList<>();
		for(int x : actualIterable) {
			actual.add(x);
		}
		
		List<Integer> expected = Arrays.asList(289,72,89,292,498,67,288,701,227,300);
	}
	
	
	
	@Test
	void recommend10Test() {
		Iterable<Integer> actualIterable = friend.recommend10(2);
		
		List<Integer> actual = new ArrayList<>();
		for(int x : actualIterable) {
			actual.add(x);
		}
		
		List<Integer> expected = Arrays.asList(51,72,67,1246,1104,511,159,1001,993,55);
		
		assertEquals(actual, expected);

		
	}
	

}
