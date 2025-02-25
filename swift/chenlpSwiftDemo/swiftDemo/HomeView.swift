//
//  HomeView.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import SwiftUI

struct HomeView: View {
    @ObservedObject var listData = getData()
    var body: some View {
        NavigationView {
            Group(){
                if listData.data.count>0 {
                    PostListView()
                }else{
                    Button {
                        self.listData.updateData()
                    } label: {
                        Text("点击加载!!!")
                    }
                }                
            }
            .navigationBarTitle("App")
        }
       
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView().environmentObject(UserData())
    }
}

