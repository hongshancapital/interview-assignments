//
//  DemoContentCell.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//

import SwiftUI

struct DemoContentCell: View {
    
    var progress: ProgressView = ProgressView()
    var model: ContentResult?
    @Binding var selecteState: [String:String]
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
            /// icon View
            ImageLoaderView(
                url: self.model?.artworkUrl512,
                placeholder: {
                    Image("icon_placeholder")
                        .resizable()
                        .renderingMode(.original)
                        .cornerRadius(15)
                        .frame(width: 64, height: 64)
                        .overlay(self.progress.progressViewStyle(CircularProgressViewStyle(tint: .white)))
                },
                image: {
                    $0.resizable()
                        .renderingMode(.original)
                        .cornerRadius(15)
                        .frame(width: 64, height: 64)
                        .overlay(self.progress.progressViewStyle(CircularProgressViewStyle(tint: .clear)))
                },
                completion: { image in
                    
                }
            )
            
            /// 空格
            Spacer().frame(width: 5, height: 5, alignment: .center)
            
            /// 文本显示
            VStack(alignment: .leading, spacing: 5) {
                Text((self.model?.artistName)!)
                    .font(.system(size: 17, weight: .bold))
                Text((self.model?.description)!)
                    .lineLimit(2)
                    .font(.system(size: 15, weight: .light))
            }
            
            /// 空格
            Spacer().frame(width: 15, height: 5, alignment: .center)
            
            /// ❤ 按钮
            Button {
                let isSelect = self.selecteState["\(self.model!.id)"]
                if isSelect != "" {
                    self.selecteState[(String(describing: self.model?.id))] = String(describing: self.model?.id)
                } else {
                    self.selecteState[(String(describing: self.model?.id))] = ""
                }
            } label: {
              VStack(spacing: 0) {
                  Image((self.selecteState[(String(describing: self.model?.id))] != nil && self.selecteState[(String(describing: self.model?.id))] != "")  ? "icon_heart_red" : "icon_heart_gray")
              }
              .foregroundColor(.white)
              .cornerRadius(10)
              .frame(width: 20, height: 20, alignment: .trailing)
            }
            .frame(width: 20, height: 20, alignment: .trailing)
        }
        .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10))
    }
}

//struct DemoContentCell_Previews: PreviewProvider {
//    static var previews: some View {
//        DemoContentCell()
//    }
//}
