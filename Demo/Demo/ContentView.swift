//
//  ContentView.swift
//  Demo
//
//  Created by mac on 2022/7/19.
//

import SwiftUI
import UIKit

struct ContentView: View {
    @ObservedObject private var viewModel =  ContentViewModel()
    
    @State var isRefreshing:Bool = false
    
    var body: some View {
        List(viewModel.datas,id:\.trackId){app in
            AppRow(model: app)
                .background(.blue)
                .listRowInsets(EdgeInsets(top: 0, leading: 20, bottom: 10, trailing: 20))
                .listRowSeparator(.hidden)
                .listRowBackground(RowBackground())
                .cornerRadius(10)
        }
        .listStyle(.plain)
        .background(Color.init(red: 244/255.0, green: 244/255.0, blue: 247/255.0))
        .navigationTitle("App")
        .background(PullRefresh(isRefreshing: $isRefreshing, refresh: {pullRefresh in
            viewModel.refresh();
            DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                self.isRefreshing = false
                pullRefresh.noMoreData = viewModel.noMoreData
                pullRefresh.label.text = "loading more ···"
                pullRefresh.refreshControl.endRefreshing()
            }
        },loadMore: {pullRefresh in
            viewModel.loadMore()
            DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                self.isRefreshing = false
                pullRefresh.refreshControl.endRefreshing()
                if viewModel.noMoreData ==  true {
                    pullRefresh.endWithNoMoreData()
                }
            }
        }))
        .onAppear(){
            viewModel.refresh()
        }
       
    }
}

struct AppRow: View {
    
    @State var model:AppModel
    
    var body: some View {
        HStack{
            HStack{
                AsyncImage(url: URL(string: model.artworkUrl100)){      image in
                    image.resizable()
                    .cornerRadius(8)
                    .overlay(RoundedRectangle(cornerRadius: 8,style: .continuous).stroke(.gray,lineWidth: 1))
                }placeholder: {
                    ProgressView()
                }
                    .frame(width: 60, height: 60)
                VStack(alignment: .leading){
                    Text(model.trackName)
                        .bold()
                        .lineLimit(1)
                        .clipped()
                        .font(.system(size: 18))
                    Text(model.description)
                        .lineLimit(2)
                        .font(.system(size: 14))
                }
                Spacer()
                Image(systemName: model.collect == true ?  "suit.heart.fill":"heart")
                    .foregroundColor(model.collect==true ? Color.red:Color.gray)
                    .onTapGesture {
                        if model.collect == true{
                            model.collect = false;
                        }else{
                            model.collect = true
                        }
                    }
            }
        }
        .padding()
        .background(.white)
    }
        
}

struct RowBackground: View {
    var body: some View {
        Spacer()
    }
}

