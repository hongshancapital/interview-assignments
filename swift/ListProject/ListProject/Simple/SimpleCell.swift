//
//  SimpleCell.swift
//  ListProject
//
//  Created by shencong on 2022/6/8.
//

import SwiftUI

struct SimpleCell: View {
    let item: ItemModel

    @EnvironmentObject var listData: ListData
    var currIndex:Int {
        listData.items.firstIndex(where: {$0.id == item.id})!
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
            HStack(alignment: .center) {
                // 网络图片加载
                AsyncImage(url: URL(string: item.artworkUrl60)) { image in
                    image.resizable() // 填充
                } placeholder: {
                    SimpleRefreshingView()
                }
                .frame(width: 60, height: 60) // 大小
                .clipShape(RoundedRectangle(cornerRadius: 5))
                
                VStack(alignment: .leading, spacing: 10){
                    Text(item.trackName).lineLimit(1).font(Font.system(size: 14))
                    Text(item.releaseNotes!).lineLimit(2).font(Font.system(size: 12))
                }.padding(.leading,5)
                Spacer()
                
                // 收藏按钮
                Button(action: {
                    // 改变爱心的选中状态
                    self.listData.items[self.currIndex].isCollect.toggle()
                    self.listData.allFavorite[item.id] = self.listData.items[self.currIndex].isCollect
                }){
                    if item.isCollect {
                        Image(systemName: "heart.fill").resizable().frame(width: 20, height: 20, alignment: .center).foregroundColor(Color.red)
                    } else {
                        Image(systemName: "heart").resizable().frame(width: 20, height: 20, alignment: .center).foregroundColor(Color.gray)
                    }
                }.frame(width: 40, height: 40, alignment: .center)
            }
            .padding(.all,10).background(Color.white)
            .cornerRadius(5)
        }
        .padding(EdgeInsets(top: 2.5, leading: 10, bottom: 2.5, trailing: 10))
        .background(RGB(244, 244, 247))
    }
}

struct SimpleCell_Previews: PreviewProvider {
    static var previews: some View {
        ScrollView {
            ForEach(allListData) { item in
                SimpleCell(item: item)
            }
        }
        .previewLayout(.sizeThatFits)

    }
}
