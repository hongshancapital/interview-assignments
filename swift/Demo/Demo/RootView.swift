//
//  RootView.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import SwiftUI

struct RootView: View {
    
    var body: some View {
        NavigationView {
            ArtistListView().navigationTitle("App")
        }
    }
    
}

struct RootView_previews: PreviewProvider {
    
    static var previews: some View {
        RootView()
    }
    
}
