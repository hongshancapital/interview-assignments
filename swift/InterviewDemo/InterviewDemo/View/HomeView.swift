//

//
//  HomeView.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/1/31.
//  
//
    

import SwiftUI

struct HomeView: View {
    var body: some View {
        NavigationView {
            AppDataList()
                .background(Color(red: 244/255.0, green: 244/255.0, blue: 247/255.0).edgesIgnoringSafeArea(.all))
                .navigationTitle("App")
        }
    }
}
