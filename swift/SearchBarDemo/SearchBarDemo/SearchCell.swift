//
//  SearchCell.swift
//  SearchBarDemo
//
//  Created by Jaydon Qin on 2021/9/26.
//

import SwiftUI

struct SearchCell: View {
    var searchModel: SearchItemModel?
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text(searchModel?.title ?? "")
                    .font(.title)
                Text(searchModel?.subTit ?? "")
                    .font(.subheadline)
            }
            Spacer()
            Text(searchModel?.money ?? "")
                .font(.subheadline)
                .foregroundColor((searchModel?.style == "0") ? Color.gray : Color.blue)

        }.padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10))
    }
}

struct SearchCell_Previews: PreviewProvider {
    static var previews: some View {
        if let arr = searchModels {
            let modle = arr[0].content[0]
            SearchCell(searchModel:modle)
        }
//        SearchCell(searchModel: searchModels?[1].content ?? "")
//        Group{
//            SearchCell(searchModel: searchModels?[0].content ?? "")
//            SearchCell(searchModel: searchModels?[1].content ?? "")
//        }
//        .previewLayout(.fixed(width: 300, height: 70))
    }
}
