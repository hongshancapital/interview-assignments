//
//  ContentView.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//

import SwiftUI

struct ContentView: View {
    
   
    var body: some View {
        NavigationView { 
            RefreshListView()
                .background(Color(uiColor: .systemBackground))
                .navigationBarTitle(Text("App"))
        }
        
    }
    
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


