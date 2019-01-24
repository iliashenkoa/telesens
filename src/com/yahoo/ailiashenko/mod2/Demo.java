package com.yahoo.ailiashenko.mod2;

import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) {
		RailwayStation rsKh = new RailwayStation("Харьков", RegionLabel.ЮЖН);
		RailwayStation rsNovos = new RailwayStation("Новоселовка", RegionLabel.ЮЖН);
		RailwayStation rsLiman = new RailwayStation("Лиман", RegionLabel.ДОН);
		RailwayStation rsMerefa = new RailwayStation("Мерефа", RegionLabel.ЮЖН);
		RailwayStation rsLozovaya = new RailwayStation("Лозовая", RegionLabel.ЮЖН);

		NetworkSegment<RailwayStation> rsKhNovos = new NetworkSegment<>(rsKh, rsNovos, 2, 5);
		NetworkSegment<RailwayStation> rsNovosLiman = new NetworkSegment<>(rsNovos, rsLiman, 183, 180);
		NetworkSegment<RailwayStation> rsNovosMerefa = new NetworkSegment<>(rsNovos, rsMerefa, 23, 30);
		NetworkSegment<RailwayStation> rsMerefaLoz = new NetworkSegment<>(rsMerefa, rsLozovaya, 123, 120);
		NetworkSegment<RailwayStation> rsLozLiman = new NetworkSegment<>(rsLozovaya, rsLiman, 132, 250);

		List<NetworkSegment<RailwayStation>> listOfSegments = new ArrayList<>();
		listOfSegments.add(rsKhNovos);
		listOfSegments.add(rsNovosLiman);
		listOfSegments.add(rsNovosMerefa);
		listOfSegments.add(rsMerefaLoz);
		listOfSegments.add(rsLozLiman);

		RailwayNetwork rNet = new RailwayNetwork(listOfSegments);
		System.out.println(rNet.toString());
		System.out.println("MAX SPEED ON SEGMENT! " + rNet.maxSpeedSegment());
		System.out.println(System.lineSeparator() + rNet.sortByLinks().toString());
		System.out.println(System.lineSeparator() + rNet.searchMax1Hop(rsKh));

		System.out.println(
				"Fastest way in min from " + rsKh + " to " + rsLozovaya + " = " + rNet.fastestWay(rsKh, rsLozovaya));
	}

}
