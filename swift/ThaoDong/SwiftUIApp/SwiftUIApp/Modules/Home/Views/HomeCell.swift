//
//  HomeCell.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//


import SwiftUI
import SDWebImageSwiftUI

struct HomeCell: View {
    @State var home: Home
    @State var iconImage: UIImage?
    @State var imageShow = false
    
    var body: some View {
        VStack {
            HStack {
                Spacer().frame(width:10)
                ZStack {
                    WebImage( url: URL(string: home.imageUrl))
                        .onSuccess(
                            perform: { _, _, _ in
                                imageShow = true
                            })
                        .resizable()
                        .frame(width:50, height: 50)
                    if !imageShow {
                        ProgressView()
                    }
                }
                .frame(width:50, height: 50)
                .cornerRadius(8)
                VStack(alignment: .leading) {
                    Text(home.title)
                    Text(home.content).font( .body)
                }
                Button(action: {
                    home.isFavorite = !home.isFavorite
                }, label: {
                    Image(systemName: home.isFavorite ? "heart.fill" : "heart")
                        .foregroundColor(home.isFavorite ?  .red : .gray)
                        .frame(width: 50, height: 50)
                    
                })
                    .buttonStyle(.borderless)
                    
                Spacer()
            }
            .frame(height:70)
            .cornerRadius(12)
            .background(Color.white)
        }
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        .frame(height: 80)
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
        
    }
}
