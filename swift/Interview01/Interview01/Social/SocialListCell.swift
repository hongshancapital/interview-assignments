//
//  SocialListCell.swift
//  Interview01
//
//  Created by chenzhe on 2022/6/21.
//

import SwiftUI

struct SocialListCell: View {
    var model: SocialModel
    var body: some View {
        HStack {
            AsyncImage(
                url: URL(string: model.coverurl)
            ) { image in
                image.resizable()
            } placeholder: {
                Color.red
            }
            .frame(width: 88, height: 88) // 大小
            .clipShape(RoundedRectangle(cornerRadius: 16))
            VStack(alignment: .leading, spacing: 10, content: {
                Text(model.title)
                    .foregroundColor(Color.black)
                    .font(Font.system(size: 18,
                                      weight: Font.Weight.bold,
                                      design: Font.Design.default))
                    .lineLimit(1)
                Text(model.bio)
                    .foregroundColor(Color.black)
                    .font(
                        Font.system(size: 18,
                                    weight: Font.Weight.medium,
                                    design: Font.Design.default))
                    .lineLimit(2)
        
            })
            Spacer()
        }.padding(
            EdgeInsets(
                top: 0,
                leading: 16,
                bottom: 0,
                trailing: 16
            )
        )
    }
}

struct SocialListCell_Previews: PreviewProvider {
    static var previews: some View {
        let data: [SocialModel] = [
            SocialModel(
                id: "1",
                coverurl: "https://img1.doubanio.com/view/photo/l/public/p2292035027.jpg",
                title: "biaoit",
                bio: "biaotiskdhksjdhfkasjdfhaksdjfhaksjhfksjdhdfskdjfhksdfhksdhfksjdhfsdjfkhsdfhjk",
                isLike: false),
            SocialModel(
                id: "2",
                coverurl: "https://img1.doubanio.com/view/photo/l/public/p2292035027.jpg",
                title: "biaoit",
                bio: "biaotiskdhksjdhfkasjdfhaksdjfhaksjhfksjdhdfskdjfhksdfhksdhfksjdhfsdjfkhsdfhjk",
                isLike: false),
            SocialModel(
                id: "3",
                coverurl: "https://img1.doubanio.com/view/photo/l/public/p2292035027.jpg",
                title: "biaoit",
                bio: "biaotiskdhksjdhfkasjdfhaksdjfhaksjhfksjdhdfskdjfhksdfhksdhfksjdhfsdjfkhsdfhjk",
                isLike: false)]
        Group {
            SocialListCell(model: data[0])
            SocialListCell(model: data[1])
            SocialListCell(model: data[2])
        }.previewLayout(.fixed(width: UIScreen.main.bounds.width, height: 120))
        
    }
}
