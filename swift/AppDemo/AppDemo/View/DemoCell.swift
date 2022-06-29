//
// Created by Jeffrey Wei on 2022/6/27.
//

import SwiftUI

struct DemoCell: View {
    private let model: DemoModel

    init(model: DemoModel) {
        self.model = model
    }

    internal var body: some View {
        HStack {
            AsyncImage(
                    url: URL(string: model.icon),
                    content: {
                        $0.resizable()
                    },
                    placeholder: {
                        ProgressView()
                    })
                    .frame(width: 48, height: 48)
            VStack(alignment: .leading, spacing: 8) {
                Text(model.artistName).font(.title3).lineLimit(1)
                Text(model.description).font(.body).lineLimit(2)
            }
            Spacer()
            Image(systemName: "heart").resizable().frame(width: 20, height: 20)
        }
                .padding(12)
                .background(Color.white)
                .cornerRadius(8)
    }
}
