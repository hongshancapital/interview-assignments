//
//  ContentView.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/4.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Hello, world!")
                .padding()
            
            
            ImageView(imageUrl: "https://lmg.jj20.com/up/allimg/1114/0G020114924/200G0114924-15-1200.jpg")
            
            ImageView(imageUrl: "https://lmg.jj20.com/up/allimg/1114/0G020114924/200G0114924-3-1200.jpg")
            
            ImageView(imageUrl: "https://lmg.jj20.com/up/allimg/1114/0G020114924/200G0114924-2-1200.jpg")
        
            Spacer()
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
