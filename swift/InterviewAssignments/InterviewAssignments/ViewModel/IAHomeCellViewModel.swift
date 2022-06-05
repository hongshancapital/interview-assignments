//
//  IAHomeCellViewModel.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import Foundation

class IAHomeCellViewModel: ObservableObject, Identifiable {

    @Published var model: IAAppInfoModel

    init(_ model: IAAppInfoModel) {
        self.model = model
    }

    func toggleFavorite() {
        model.isFavorite.toggle()
    }
}
