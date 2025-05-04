package assignment4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class LastFMRecommender {
	
	private Graph friends;
	private ArrayList<String> artists = new ArrayList<>();
	EdgeWeightedDigraph userArtistsWeighted;

	public LastFMRecommender(String file, String file2, String file3) {
		
		int verticies = 0;
		
		In in = new In(file);
		in.readLine();
		
		
		//Calculates # of verticies which are then passed to the graph
		while(in.hasNextLine()) {
			verticies++;
			in.readLine();
		}
		
		friends = new Graph(verticies);
		
		in = new In(file);
		in.readLine();
		
		//populates graph
		while(in.hasNextLine()) {

			String user = in.readString();

			String userFriend = in.readString();
			
			//checks to see if edge is already in graph
			if(!hasEdge(Integer.parseInt(user),Integer.parseInt(userFriend)))
				friends.addEdge(Integer.parseInt(user),Integer.parseInt(userFriend));

		}
		
		
		verticies = 0;
		
		in = new In(file2);
		
		//calculates verticies for Digraph
		while(in.hasNextLine()) {
			verticies++;
			in.readLine();
		}
		
		in = new In(file2);
		in.readLine();

		
		userArtistsWeighted = new EdgeWeightedDigraph(verticies);
		
		//reads user,artist, and weight and adds the edge to the digraph
		while(in.hasNextLine()) {
			String info = in.readLine();
			String[] infoSplit = info.split("\t");
			
			int user = Integer.parseInt(infoSplit[0]);
			int artist = Integer.parseInt(infoSplit[1]);
			int weight = Integer.parseInt(infoSplit[2]);
			
			//System.out.println(user + " " + artist);
			
			
			userArtistsWeighted.addEdge(new DirectedEdge(user,artist,weight));
		}
		
		in = new In(file3);
		in.readLine();
		
		//adds all artist in arrayList which is used to grab their name
		while(in.hasNextLine()) {
			String ArtistInfo = in.readLine();
			String[] ArtistInfoSplit = ArtistInfo.split("\t");
			
			String artist = ArtistInfoSplit[1];
			
			artists.add(artist);
			
			

		}
		
	}
	
	
	/*
	 * hasEdge 
	 * Desc: Checks to see if current friends adjList contains the second edge
	 * @param first: The adjList we want to go through
	 * @param second: The edge we are looking for within the adjList
	 * @return boolean: True if edge is within the first adjList, false otherwise
	 */
	private boolean hasEdge(int first, int second) {
		Iterable<Integer> firstAdj = friends.adj(first);
		
		for(int x : firstAdj) {
			if(x == second)
				return true;
		}
		return false;
		
	}
	
	
	/*
	 * listFriends 
	 * @Desc: returns and prints all friends of the user
	 * @param user: users friends we want to find
	 * @returns Iterable<Integer>: returns Iterable which contains all friends
	 */
	public Iterable<Integer> listFriends(int user) {		
		return friends.adj(user);
	}
	
	
	/*
	 * Common Friends
	 * @Desc: Finds friends in common user1 and user2 have
	 * @param user1: first user used in comparison
	 * @param user2: second user used in comparison
	 * @returns Iterable<Integer>: returns iterable which contains all common friends
	 */
	public Iterable<Integer> commonFriends(int user1, int user2){
		Iterable<Integer> friendsList1 = friends.adj(user1);
		Iterable<Integer> friendsList2 = friends.adj(user2);
		
		ArrayList<Integer> commonFriends = new ArrayList<Integer>();
		
		//adds friends to commonFriends if friend is found in user2
		for(int x : friendsList1) {
			for(int y : friendsList2) {
				if(x == y)
					commonFriends.add(x);
			}
		}
		
		System.out.println(user1 +"'s friends in common with " + user2 + " :");
		for(int x : commonFriends) {
			System.out.println(x);
		}
		
		return commonFriends;
				
	}
	
	
	/*
	 * List Artists 
	 * Desc: Prints out common artists between 2 users
	 * @param user1: first user used in comparison
	 * @param user2: second user used in comparison
	 * @return Iterable<Integer>: returns iterable which contains common friends
	 */
	public Iterable<Integer> listArtists(int user1, int user2){
		Iterable<DirectedEdge> userArtists1 = userArtistsWeighted.adj(user1);
		Iterable<DirectedEdge> userArtists2 = userArtistsWeighted.adj(user2);
		
		ArrayList<Integer> commonArtists = new ArrayList<Integer>();
		
		
		for(DirectedEdge artist1 : userArtists1) {
			for(DirectedEdge artist2 : userArtists2) {
				if(artist1.to() == artist2.to()) {
					commonArtists.add(artist1.to());
				}
			}
		}
		
		System.out.println("Common artists between " + user1 + " and " + user2 + ":");
		for(int x : commonArtists) {
			System.out.println(artists.get(x));
		}
		
		System.out.println();
		
		return commonArtists;
	}
	
	
	
	/*
	 * top10
	 * Desc: Sums up total weight of each user artist relation and returns the top 10
	 * most listened to artists
	 * 
	 * @return Iterable<Integer>: Returns Iterable Integer which contains top 10 artists
	 */
	public Iterable<Integer> top10(){

		Map<Integer,Integer> artistsListens = new HashMap<Integer,Integer>();
		
		for(int x = 2; x < userArtistsWeighted.V(); x++) {
			for(DirectedEdge user : userArtistsWeighted.adj(x)) {
                artistsListens.put(user.to(), (int) (artistsListens.getOrDefault(user.to(), 0) + user.weight()));

			}
		}
		
		
		//Converts artistsListens map into arrayList
		List<Map.Entry<Integer, Integer>> artistSorted = new ArrayList<>(artistsListens.entrySet());
		
		//sorts artists in descending order
		artistSorted.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

		ArrayList<Integer> topArtists = new ArrayList<Integer>();
		
		System.out.println("\nTop 10 artists via listens: ");

		for (int i = 0; i < 10; i++) {
			
			System.out.println(artists.get(artistSorted.get(i).getKey()));
			topArtists.add(artistSorted.get(i).getKey());
			
        }
		
		System.out.println();
		
		return topArtists;
		

	}
	
	
	/*
	 * recommend10
	 * Desc: Returns the top10 artists, depending on the user and their friends
	 * @param user: user we want to cater the recommendations too
	 * @return Iterable<Integer>: List of the users top 10 recommended artists
	 */
	public Iterable<Integer> recommend10(int user){
		
		Map<Integer, Integer> artistListens = new HashMap<Integer, Integer>();
		
		//adds up listening counts for friends of user into artistListens
		for(int friends :listFriends(user)) {
			for(DirectedEdge friendsArtists : userArtistsWeighted.adj(friends)) {
                artistListens.put(friendsArtists.to(),  (int) (artistListens.getOrDefault(friendsArtists.to(), 0) + friendsArtists.weight()));

			}
			
		}
		
		//Takes into account users listening activity
		for(DirectedEdge userArtists : userArtistsWeighted.adj(user)) {
            artistListens.put(userArtists.to(),  (int) (artistListens.getOrDefault(userArtists.to(), 0) + userArtists.weight()));

		}
		
		
		List<Map.Entry<Integer, Integer>> artistSorted = new ArrayList<>(artistListens.entrySet());
		artistSorted.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
		
		ArrayList<Integer> recommended = new ArrayList<Integer>();

		System.out.println("\nRecommending top 10 artists via user and friends: ");
		for (int i = 0; i < 10; i++) {
			
            System.out.println(artists.get(artistSorted.get(i).getKey()));
            recommended.add(artistSorted.get(i).getKey());
            
        }
		
		return recommended;

	}
	
}
