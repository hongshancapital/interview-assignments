//
//  ListRowCell.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import SwiftUI

struct ListRowCell: View {
    @StateObject var viewModel: ApplicationVM
    var item: ApplicationInfo
    @State var isFavorite = false {
          didSet {
              if isFavorite {
                  viewModel.likeList.insert(item.trackId)
              } else {
                  viewModel.likeList.remove(item.trackId)
              }
          }
      }


    var body: some View {
        ZStack{
            HStack{
                Spacer().frame(width: 8)
                VStack{
                    HStack(spacing: 10){
                        IconImageView(url: item.artworkUrl60 )
                            .padding(EdgeInsets(top: 8, leading: 8, bottom: 8, trailing: 4))
                        VStack(alignment:.leading , spacing: 5){
                            Text(item.trackCensoredName)
                                .font(.system(size: 17))
                            Text(item.description )
                                .font(.system(size: 15))
                                .lineLimit(2)
                        }
                        Spacer()
                        
                        Button(action: {
                            isFavorite.toggle()
                        }, label: {
                            let like = viewModel.likeList.contains(item.trackId)
                            Image(systemName: like ? "suit.heart.fill" : "suit.heart")
                                .resizable().aspectRatio(contentMode: .fit)
                                .foregroundColor(like ? .red : .gray)
                                .foregroundColor(.red)
                                .frame(width: 20, height: 20, alignment: .center)
                        }).frame(width: 40, height: 40, alignment: .center)

                    }

                }.background(.white)
                    .frame( height: 70, alignment: .center)
                    .cornerRadius(8)
                    .padding(.trailing,10)

            }
        }
    }
}
