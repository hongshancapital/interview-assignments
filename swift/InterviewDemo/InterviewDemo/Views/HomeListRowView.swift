//
//  HomeListRowView.swift
//  InterviewDemo
//
//  Created by 陈凝 on 2022/8/1.
//

import SwiftUI

struct HomeListRowView: View {
    
    let model : Result
    
    @State var isLike : Bool = false
    
    var body: some View {
        VStack{
            HStack{
                AsyncImage(url:URL(string: model.artworkUrl512 ?? "")){image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width:50,height:50)
                        .cornerRadius(10)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(Color.gray.opacity(0.8),lineWidth: 0.5)
                        )
                } placeholder: {
                    ProgressView()
                        .frame(width:50,height:50)
                }
                
                VStack(alignment:.leading){
                    Text(model.trackName ?? "")
                        .font(.callout)
                        .fontWeight(.semibold)
                        .lineLimit(1)
                    Text(model.description ?? "")
                        .font(.footnote)
                        .lineLimit(2)
                }
                
                Spacer()
                
                Image(systemName: isLike ? "heart.fill" : "heart")
                    .foregroundColor(isLike ? .red : .gray)
                    .font(.subheadline)
                    .foregroundColor(.black)
                    .onTapGesture {
                        isLike.toggle()
                    }
            }
            .padding(10)
        }
        .background(Color.white.cornerRadius(10))
    }
}

