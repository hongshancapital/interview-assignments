//
//  GetSoftwareList.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import Foundation

class GetSoftwareList: Usecase<GetSoftwareListParam, [Software]> {
    let repository: SoftwareListRepositoryProtocol
    
    init(repository: SoftwareListRepositoryProtocol) {
        self.repository = repository
    }

    override func call(params: GetSoftwareListParam) async -> Result<[Software], Failure> {
        return await self.repository.getSoftware(count: params.count)
    }
}

struct GetSoftwareListParam {
    let count: Int
}
