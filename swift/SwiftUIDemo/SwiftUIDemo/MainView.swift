//
//  MainView.swift
//  SwiftUIDemo
//
//  Created by 齐风修 on 2021/9/7.
//

import SwiftUI

struct MainView: View {
    
    let username: String
    
    var body: some View {
        VStack(alignment: .leading) {
            ZStack {
                Image.init("")
                    .resizable()
                    .ignoresSafeArea(edges: .top)
                    .frame(height: 120)
                    .background(greenColor)
                    .offset(y: -200)
                Image.init("")
                    .resizable()
                    .ignoresSafeArea(edges: .top)
                    .frame(height: 300)
                    .background(greenColor)
                    .cornerRadius(16)
                VStack {
                    Text(String(username.first!))
                        .font(.title)
                        .foregroundColor(.white)
                        .frame(width: 66, height: 66, alignment: .center)
                        .background(grayColor)
                        .cornerRadius(33)
                        .padding()
                    
                    Text(username)
                        .font(.body)
                        .foregroundColor(.white)
                        .cornerRadius(33)
                }
                
                
                
            }
            
            
            Spacer()
        }
    }
    
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            MainView(username: "Manfred")
        }
    }
}
