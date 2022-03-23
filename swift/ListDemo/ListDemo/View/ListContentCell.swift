//
//  ListContentCell.swift
//  ListDemo
//
//  Created by mac on 2022/3/18.
//

import SwiftUI
import SDWebImageSwiftUI

struct ListContentCell: View {
    var model: ListModel

    var body: some View {
        //水平布局--垂直布局
        HStack {
            
            //leftimage
            WebImage(url: URL(string: model.artworkUrl100))
                .resizable()
                .placeholder{Color.gray}
                .indicator(.activity)
                .scaledToFit()
                .frame(height:40)
                .frame(width:40)
                .clipped()
                .cornerRadius(10)
                .padding(5)
            
            //middletext
            VStack(alignment: .leading, spacing: 1) {
                
                //title
                Text(model.sellerName)
                    .font(.system(size: 18))
                    .frame(height: 24)
                
                //description
                Text(model.description)
                    .font(.system(size: 14))
                    .frame(height: 40)
            }
            
            Spacer()
            
            //rightheart
            Image("heartIcon")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 20, height: 20)
                .padding(2)
        }
        .frame(height: 64)
        .padding(10)
        .background(Color.white)
        .cornerRadius(5)
        .padding(.leading, 10)
        .padding(.top, 10)
        .padding(.trailing, 10)
        .background(Color(UIColor(displayP3Red: 0.95, green: 0.95, blue: 0.95, alpha: 1.0)))
    }
    
}

struct ListContentCell_Previews: PreviewProvider {
    static var previews: some View {
        ListContentCell(model: ListModel.init(trackId: 0, description: "", artworkUrl100: "", sellerName: ""))
    }
}

