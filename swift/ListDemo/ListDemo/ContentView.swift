//
//  ContentView.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import SwiftUI

struct ContentView: View {
    private let viewModelFactory = ViewModelFactory()

    var body: some View {
        SearchListView(viewModel: viewModelFactory.makeMovieListViewModel())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
