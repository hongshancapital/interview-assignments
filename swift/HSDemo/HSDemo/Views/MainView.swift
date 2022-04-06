//
//  ContentView.swift
//  HSDemo
//

//

import SwiftUI

struct MainView: View {
    @StateObject private var artworkFetcher: ArtWorkFetcher = .init()

    var body: some View {
        if artworkFetcher.isLoading {
            LoadingView()
        } else if artworkFetcher.errorMessage != nil {
            ErrorView(artworkFetcher: artworkFetcher)
        } else {
            ArtWorkListView(artworkFetcher: artworkFetcher)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
