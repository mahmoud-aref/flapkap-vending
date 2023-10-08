package com.flapkap.vending.infrastructure.security.model;

public interface UserAccessPermissions {

  String HAS_SELLER_ROLE = "hasAnyAuthority('SELLER')";
  String HAS_BUYER_ROLE = "hasAnyAuthority('BUYER')";
  String HAS_SELLER_OR_BUYER_ROLE = "hasAnyAuthority('SELLER', 'BUYER')";
}
