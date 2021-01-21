//
//  ContentView.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import SwiftUI

struct ContentView: View {
    
    var body: some View {
        ZStack {
            Color("background_gray")
                .ignoresSafeArea()
            NavigationView {
                VStack {
                    Text("Search")
                        .font(.largeTitle)
                        .bold()
                        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                        .padding(.top, 36)
                        .padding(.leading,16)
                    
                    NavigationLink(destination: GoodsList()) {
                        SearchBar(hideImage: .constant(true), text: .constant(""))
                            .padding([.leading,.top,.trailing], 10)
                            .allowsHitTesting(false)
                    }
                    Spacer()
                }
                .navigationBarHidden(true)
            }

        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
