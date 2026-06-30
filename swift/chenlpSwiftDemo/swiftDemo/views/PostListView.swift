//
//  ContentView.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import Foundation
import UIKit
import SwiftUI
import SDWebImageSwiftUI


struct PostListView: View {
    @EnvironmentObject var userData: UserData
    
    @State var isLastOver: Bool = false
    
    @ObservedObject var listData = getData()
    var body: some View {
        
        VStack{
            List(0..<listData.data.count,id: \.self) {i in
                let post = userData.postList().results[i]
                if i == listData.data.count - 1 {
                    PostCell(post: post, data: self.listData.data[i], isLast: true, listData: listData, isLastOver: $isLastOver)
                    if $isLastOver.wrappedValue {
                        Text("No More Data")
                            .foregroundColor(.gray)
                            .fontWeight(.bold)
                                
                        .listRowInsets(EdgeInsets())
                        .frame(maxWidth: .infinity, minHeight: 50)
                        .background(Color(UIColor.systemGroupedBackground))
                    }
                }else{
                    PostCell(post: post, data: self.listData.data[i], isLast: false, listData: listData, isLastOver: $isLastOver)
                }
            }.listStyle(GroupedListStyle())
            
        
            
        }.padding(.top,-10)
    }
}

struct PostListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            PostListView()
                .environmentObject(UserData())
        }
    }
}



  
  
