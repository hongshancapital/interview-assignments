//
//  AppItemView.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import SwiftUI

struct AppItemView: View {
    
    var results: ApplyListModel.Results
    
    @State  var iconImage : UIImage? = nil
    @State var isChecked: Bool = false
    
    var body: some View{
        
        HStack {
            Rectangle()
                .frame(width: 10)
                .foregroundColor(.clear)
            
            HStack(alignment: .center, spacing: 0){
                
                Rectangle()
                    .frame(width: 5)
                    .foregroundColor(.clear)
                
                let url = URL(string: results.artworkUrl60)
                AsyncImage(url: url)
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 60, height: 60, alignment: .center)
                    .overlay(
                        RoundedRectangle(cornerRadius: 10, style: .continuous)
                            .stroke(.gray, lineWidth: 1)
                    )
                                    .cornerRadius(10)
                
                VStack(alignment: .leading, spacing: 1){
                    
                    Text(results.trackName)
                        .font(Font.system(size: 16))
                        .fontWeight(.bold)
                        .frame(
                            alignment: .leading
                        )
                    
                    Text(results.description)
                        .frame(
                            maxWidth: .infinity,
                            minHeight: 36, idealHeight: 39, maxHeight: 42,
                            alignment: .leading
                        )
                        .font(Font.system(size: 12))
                        .lineLimit(2)
                }.padding()
                
                Image(systemName: isChecked ? "suit.heart.fill" : "suit.heart")
                    .foregroundColor(isChecked ? .red : .black)
                    .frame(width: 25, height: 25, alignment: .center)
                    .onTapGesture {
                        self.isChecked.toggle()
                    }
                
                Rectangle()
                    .frame(width: 5)
                    .foregroundColor(.clear)
            }
            .frame(height: 80)
            .background(Color.white)
            .cornerRadius(10)
            
            
            Rectangle()
                .frame(width: 10)
                .foregroundColor(Color.clear)
        }
    }
    
}

