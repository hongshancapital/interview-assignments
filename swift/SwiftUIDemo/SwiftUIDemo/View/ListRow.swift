//
//  ListRow.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/17.
//

import SwiftUI

struct ListRow: View {
    @State var dataModel: DataModel
    var body: some View {
        HStack(spacing: 5) {
            NetworkImage(imageURLString: dataModel.artworkUrl60)
                .frame(width: 40, height: 40, alignment: .center)
            VStack(alignment: .leading) {
                Text(dataModel.trackName)
                    .font(.system(size: 15))
                    .bold()
                    .lineLimit(1)
                Text(dataModel.description)
                    .font(.system(size: 12))
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: "heart\(dataModel.favorite ? ".fill" : "")")
                .foregroundColor(dataModel.favorite ? .red : .black)
                .scaleEffect(dataModel.favorite ? 1.1 : 1.0)
                .onTapGesture {
                    withAnimation {
                        dataModel.favorite.toggle()
                    }
                }
        }
        .frame(maxWidth:.infinity)
        .padding(10)
        .background(Color.white)
    }
}

struct ListRow_Previews: PreviewProvider {
    static var previews: some View {
        ListRow(dataModel: DataModel(trackName: "abc", description: "testBody", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple115/v4/2e/ac/17/2eac1702-f302-b3ea-a380-8accfc2823e3/070e60c9-15ba-41fc-bc18-daffb5936c9d_iOS_5.5_01.png/392x696bb.png"))
    }
}
