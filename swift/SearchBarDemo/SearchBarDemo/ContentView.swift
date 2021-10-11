//
//  ContentView.swift
//  SearchBarDemo
//
//  Created by Kristijan Kralj on 02/04/2020.
//  Copyright © 2020 Kristijan Kralj. All rights reserved.
//

import SwiftUI
import Foundation

struct ContentView: View {
    
    @State private var searchTerm : String = ""
    @State private var isEditing : Bool = false
    
    var body: some View {

        NavigationView{
            VStack{
                SearchBar(text: $searchTerm,isEditing: $isEditing)
                if self.isEditing {
                    SearchListView(searchTerm: $searchTerm)
                }else{
                    Spacer()
                }
                
            }
            .navigationBarTitle(Text("Search"))
            .navigationBarHidden(self.isEditing)
        
            
        }
        .padding(.top, 0.0)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    
    }
   
    
}
