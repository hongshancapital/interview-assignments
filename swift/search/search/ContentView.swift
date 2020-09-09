//
//  ContentView.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    var body: some View {

            VStack{
                HStack {
                    Text("Search")
                        .font(.system(size: 50, weight: .bold))
                    .padding(.top, 50)
                    Spacer()
                    
                }
                .padding(.horizontal, 10)
                
                SearchView()
            }.background(Color("appbg")).edgesIgnoringSafeArea(.all)

    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
